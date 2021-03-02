package chatrabia.util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static int getRandom(int min,int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
