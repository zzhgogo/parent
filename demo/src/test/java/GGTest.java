import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GGTest {

    @Test
    public void t1() throws Exception{
        List<String> logsList = FileUtils.readLines(new File("/Users/zhuhao/Documents/haha.txt"));

        Set<String> set = logsList.stream()
                .map(m -> m.substring(m.indexOf("未找到")+4).trim())
                .map(m -> m.substring(0, m.indexOf(" ")).trim())
                .collect(Collectors.toSet());
        //System.out.println(set.size());
        set.stream().forEach(System.out::println);

    }
}
