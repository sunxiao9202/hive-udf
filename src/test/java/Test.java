import com.zhihuishu.GetAgentStr;
import com.zhihuishu.GetBrowser;

/**
 * @author ：SunX
 * @date ：2020/9/11 17:08
 * @description：TODO
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(GetBrowser.evaluate("Dalvik/2.1.0 (Linux; U; Android 10; MI 8 SE MIUI/V11.0.2.0.QEBCNXM)"));
        System.out.println(GetAgentStr.evaluate("Dalvik/2.1.0 (Linux; U; Android 10; MI 8 SE MIUI/V11.0.2.0.QEBCNXM)"));
    }
}
