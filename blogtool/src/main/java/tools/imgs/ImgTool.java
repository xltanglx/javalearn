package tools.imgs;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author txl
 * @Date 2020/12/27 14:01
 * @Version 1.0
 */
public class ImgTool {
    // CDN 配置
    private static final String preUrl = "https://fastly.jsdelivr.net/gh";
    private static final String gitUser = "xltanglx";
    private static final String gitRepo = "CDN";
    private static final String gitImgPath = "blog";

    // 匹配文章内所有图片的超链接
    private static final String srcImgRegex = "<(img|IMG)(.*?)(/>|></img>|>)";
    private static final String srcImgUrlRegex = "(src|SRC)=(\\\"|\\')(.*?)(\\\"|\\')";
    private static final String originImgRegex = "\\!\\[.{0,50}\\]\\(.+\\)";
    private static final String originImgUrlRegex = "(?<=\\!\\[.{0,50}\\]\\()(.+)(?=\\))";

    // 匹配图片的样式
    private static final String styleRegex = "style=\"zoom:[1-9]\\d*%;\"";

    // 新图片名称的长度
    private static final Integer newImgNameLength = 16;

    /**
     * 获取文章内图片名称到图片路径的映射
     * 注意：需使用Typora自动复制图片到./${filename}.assets文件夹
     *
     * @param blogPath 文件路径
     * @return Map<String, String>
     */
    private static Map<String, Path> getImgNameToImgPathMap(String blogPath) {
        String[] split = blogPath.split("\\\\");
        assert split.length > 0;
        split[split.length - 1] = split[split.length - 1].replaceAll(".md", "") + ".assets";
        String folderPath = String.join("\\", split);

        try (Stream<Path> stream = Files.list(Paths.get(folderPath))) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(ImgTool::isImg)
                    .collect(Collectors.toMap(
                            path -> path.getFileName().toString(),
                            Path::toAbsolutePath
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

    /**
     * 判断文件是否是图片
     *
     * @param path 文件路径
     * @return boolean
     */
    private static boolean isImg(Path path) {
        File file = path.toFile();
        String fileName = path.getFileName().toString();

        if (file.isFile() && ((fileName.endsWith(".svg")) || (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")))) {
            return true;
        }

        return false;
    }

    /**
     * 替换文章内所有图片的url，从图床——>CDN
     *
     * @param blogPath 文件路径
     * @return boolean
     */
    public static void replaceImgUrlFromPicBedtoCDN(String blogPath) {
        Map<String, Path> imgNameToImgPathMap = getImgNameToImgPathMap(blogPath);
        File file = new File(blogPath);
        try {
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            for (int i = 0; i < lines.size(); i++) {
                // 查找需要替换的行
                if (!replaceImgUrl(srcImgRegex, srcImgUrlRegex, lines, i, imgNameToImgPathMap)) {
                    replaceImgUrl(originImgRegex, originImgUrlRegex, lines, i, imgNameToImgPathMap);
                }
            }
            FileUtils.writeLines(file, "UTF-8", lines, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 匹配图片url，并替换成想要的
     *
     * @param imgRegex            图片标签
     * @param imgUrlRegex         图片url
     * @param lines               所有行
     * @param i                   第i行
     * @param imgNameToImgPathMap 图片到图片路径的映射map
     * @return boolean
     */
    private static boolean replaceImgUrl(String imgRegex, String imgUrlRegex, List<String> lines, int i, Map<String, Path> imgNameToImgPathMap) {
        String line = lines.get(i);
        Matcher matcherImg = Pattern.compile(imgRegex).matcher(line);
        if (matcherImg.find()) {
            Matcher matcherImgUrl = Pattern.compile(imgUrlRegex).matcher(line);
            // 是否是一张图片
            if (matcherImgUrl.find()) {
                String imgName = getImgNameByImgUrl(matcherImgUrl.group());
                // 本地文件夹内是否有该图片
                if (imgNameToImgPathMap.containsKey(imgName)) {
                    String newImgName = renameImg(imgName, imgNameToImgPathMap);
                    String folderPath = imgNameToImgPathMap.get(imgName).getName(imgNameToImgPathMap.get(imgName).getNameCount() - 2).toString();
                    System.out.println("替换前：" + line);
                    String style = getStyle(line);
                    String newLine = "<img src=\"" + preUrl + "/" + gitUser + "/" + gitRepo + "/" + gitImgPath + "/" + folderPath + "/" + newImgName + "\" alt=\"" + newImgName + "\" " + "border=\"0\"" + style + "/>";
                    System.out.println("替换后：" + newLine);
                    lines.remove(i);
                    lines.add(i, newLine);
                    return true;
                } else {
                    System.out.println("文件夹内未找到 " + imgName + " 图片，请再仔细核对一遍！");
                }
            }
        }
        return false;
    }

    /**
     * 根据图片的url获取图片名称
     *
     * @param imgUrl 图片url
     * @return String
     */
    private static String getImgNameByImgUrl(String imgUrl) {
        String[] split = imgUrl.split("[/\\\\]");
        assert split.length > 0;
        return split[split.length - 1].replaceAll("\"", "");
    }

    /**
     * 图片重命名，并入库
     *
     * @param imgName             图片名称
     * @param imgNameToImgPathMap 图片到图片路径的映射map
     * @return String
     */
    private static String renameImg(String imgName, Map<String, Path> imgNameToImgPathMap) {
        // 图片新名称
        String[] split1 = imgName.split("\\.");
        assert split1.length > 0;
        String newImgName = getRandomString(newImgNameLength) + "." + split1[split1.length - 1];

        // 图片新路径
        Path oldImgPath = imgNameToImgPathMap.get(imgName);
        String[] split = oldImgPath.toString().split("\\\\");
        assert split.length > 0;
        split[split.length - 1] = newImgName;
        String newImgPath = String.join("\\", split);

        // 修改图片名称
        new File(oldImgPath.toString()).renameTo(new File(newImgPath));

        return newImgName;
    }

    /**
     * 随机生成数字和字母的组合
     *
     * @param length 长度
     * @return String
     */
    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 返回原图片样式style
     *
     * @param line 图片url
     * @return
     */
    private static String getStyle(String line) {
        Matcher styleMatcher = Pattern.compile(styleRegex).matcher(line);
        return styleMatcher.find() ? " " + styleMatcher.group() + " " : " ";
    }
}