import com.dianping.cat.configuration.NetworkInterfaceManager;
import org.unidal.helper.Splitters;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class ByteLength {

    public static void main(String[] args) {
        Character str = '&';

        System.out.println(String.valueOf(str).length());

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        System.out.println(runtimeMXBean.getName());


        String ip = NetworkInterfaceManager.INSTANCE.getLocalHostAddress();
        List<String> items = Splitters.by(".").noEmptyItem().split(ip);


        byte[] bytes = new byte[4];

        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(items.get(i));
        }

        StringBuilder sb = new StringBuilder(bytes.length / 2);

        for (byte b : bytes) {
            sb.append(Integer.toHexString((b >> 4) & 0x0F));
            sb.append(Integer.toHexString(b & 0x0F));
        }

        System.out.println(sb.toString());
    }
}
