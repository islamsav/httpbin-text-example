import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static final String RU = "RUSSIAN";
    public static final String EN = "ENGLISH";
    public static final String DIGIT = "DIGIT";
    public static final String RAND = "RAND";

    /**
     * @param type   язык
     * @param length длина
     * @return Возвращает сгенерированную строку указанной длины и языка
     * @throws IllegalArgumentException выбросится в случае если передадим в аргументы неподдерживаемый язык
     */
    public static String getRandomString(String type, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            switch (type) {
                case EN:
                    sb.append(RandomStringUtils.randomAlphabetic(1).toLowerCase());
                    break;
                case RU:
                    sb.append(getRussianLetter());
                    break;
                case DIGIT:
                    sb.append(rnd(0, 9));
                    break;
                case RAND:
                    String[] arrRndChars =
                            {
                                    RandomStringUtils.randomAlphabetic(1).toLowerCase(),
                                    RandomStringUtils.randomAlphabetic(1).toUpperCase(),
                                    String.valueOf(rnd(0, 9))
                            };
                    sb.append(arrRndChars[rnd(0, arrRndChars.length - 1)]);
                    break;
                default:
                    throw new IllegalArgumentException(String.format(ErrorMessages.INCORRECT_PARAMETER, type));
            }
        }
        return new String(sb);
    }

    public static void main(String[] args) {
        System.out.println(getRandomString(RAND, 10));
    }

    /**
     * @return Случайная русская буква
     */
    private static String getRussianLetter() {
        int leftLimit = 1040;
        int rightLimit = leftLimit + 33;
        String res = "";
        int a = rnd(leftLimit, rightLimit);
        char symbol = (char) a;
        res += symbol;
        return res;
    }

    /**
     * @param min начало диапазона
     * @param max конец диапазона
     * @return возвращает рандомное число в указанном диапазоне
     */
    public static int rnd(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * @param lvl уровень стектрейса
     * @return возвращает имя метода
     */
    public static String getRunningMethodName(int lvl) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length >= lvl) {
            return stackTrace[lvl].getMethodName();
        } else {
            return "none method";
        }
    }
}
