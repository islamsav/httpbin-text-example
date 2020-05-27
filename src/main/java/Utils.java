import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static final String RU = "RUSSIAN";
    public static final String EN = "ENGLISH";

    /**
     * @param lang язык
     * @param length длина
     * @return Возвращает сгенерированную строку указанной длины и языка
     * @throws IllegalArgumentException выбросится в случае если передадим в аргументы неподдерживаемый язык
     */
    public static String getRandomString(String lang, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            switch (lang) {
                case EN:
                    sb.append(RandomStringUtils.randomAlphabetic(1).toLowerCase());
                    break;
                case RU:
                    sb.append(getRussianLetter());
                    break;
                default:
                    throw new IllegalArgumentException(String.format(ErrorMessages.INCORRECT_PARAMETER, lang));
            }
        }
        return new String(sb);
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
}
