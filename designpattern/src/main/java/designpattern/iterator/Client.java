package designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 创建学院
        List<College> collegeList = new ArrayList<College>();

        // 计算机学院
        ComputerCollege computerCollege = new ComputerCollege();
        collegeList.add(computerCollege);
        // 信息学院
        InfoCollege infoCollege = new InfoCollege();
        collegeList.add(infoCollege);

        OutPutImpl outPutImpl = new OutPutImpl(collegeList);
        outPutImpl.printCollege();
    }

}
