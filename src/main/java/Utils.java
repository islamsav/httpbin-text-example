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

    /**
     * @return возвращает имя метода вызванного уровнем выше
     */
    public static String getRunningMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
