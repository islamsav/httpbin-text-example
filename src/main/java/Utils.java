public class Utils {

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
