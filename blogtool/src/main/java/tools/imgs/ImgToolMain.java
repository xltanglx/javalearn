package tools.imgs;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author txl
 * @date 2023/1/2 15:29
 */
public class ImgToolMain {
    @Test
    public void testReplaceSingleImgNameTool() {
        String blogPath = "E:\\学习\\MyBlog\\CDN\\blog\\MySQL 性能调优.md";
        ImgTool.replaceImgUrlFromPicBedtoCDN(blogPath);
    }

    @Test
    public void testReplaceAllImgNameTool() {
        String folderPath = "G:\\MyBlog\\CDN\\blog";
        File[] files = new File(folderPath).listFiles();
        assert files != null;
        ArrayList<File> list = (ArrayList<File>) Arrays.stream(files)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(".md"))
                .collect(Collectors.toList());
        for (File file : list) {
            System.out.println(file.getAbsolutePath());
            ImgTool.replaceImgUrlFromPicBedtoCDN(file.getAbsolutePath());
        }
    }
}
