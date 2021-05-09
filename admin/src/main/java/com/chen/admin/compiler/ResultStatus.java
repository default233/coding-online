package com.chen.admin.compiler;



/**
 * @author danger
 * @date 2021/4/24
 */
public enum ResultStatus {
    ACCEPTED(0, "通过！"),
    WRONG_ANSWER(1, "错误答案！"),
    PRESENTATION_ERROR(2, "输出格式错误！"),
    TIME_LIMIT_EXCEEDED(3, "超出了题目的内存限制！"),
    MEMORY_LIMIT_EXCEEDED(4, "超出了题目的时间限制！"),
    RUNTIME_ERROR(5, "运行时错误！"),
    COMPILATION_ERROR(6, "编译错误！"),
    RESTRICTED_FUNCTION(7, "使用了不安全的函数！");

    private final int value;
    private final String description;

    ResultStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ResultStatus valueOf(int statusCode) {
        ResultStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return status;
        }
    }

    public int value() {
        return this.value;
    }

//    @Nullable
    public static ResultStatus resolve(int statusCode) {
        ResultStatus[] statuses = values();
        int length = statuses.length;
        for (int i = 0; i < length; i++) {
            ResultStatus status = statuses[i];
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ResultStatus{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
