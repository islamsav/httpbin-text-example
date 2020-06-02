import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class DataGen {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataGen.class);

    public static final String EN = "ENGLISH";
    public static final String DIGIT = "DIGIT";
    public static final String RAND_CHARS = "RAND";

    /**
     * @param type   тип
     * @param length длина
     * @return Возвращает сгенерированную строку указанной длины и типа
     * @throws IllegalArgumentException выбросится в случае если передадим в аргументы неподдерживаемый язык
     */
    public static String getRandomString(String type, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            switch (type) {
                case EN:
                    sb.append(RandomStringUtils.randomAlphabetic(1).toLowerCase());
                    break;
                case DIGIT:
                    sb.append(rnd(0, 9));
                    break;
                case RAND_CHARS:
                    String[] arrRndChars =
                            {
                                    RandomStringUtils.randomAlphabetic(1).toLowerCase(),
                                    String.valueOf(rnd(0, 9)),
                                    RandomStringUtils.randomAlphabetic(1).toUpperCase()
                            };
                    sb.append(arrRndChars[rnd(0, arrRndChars.length - 1)]);
                    break;
                default:
                    throw new IllegalArgumentException(String.format(ErrorMessages.INCORRECT_PARAMETER, type));
            }
        }
        final String value = new String(sb);
        LOGGER.info("generate value - {}", value);
        return value;
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
