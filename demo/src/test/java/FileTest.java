import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


@Slf4j
public class FileTest {

    @Test
    public void getDirServePath() {
        String s = FileUtil.mainName("assets/");
        log.info(s);

        s = FileUtil.mainName("/usr/local/assets/");
        log.info(s);

        s = FileUtil.mainName("assets");
        log.info(s);

        s = FileUtil.mainName("assets");
        log.info(s);
    }
}
