package br.com.mjv.quizz.domain.config.result;

import java.util.function.Function;

public class ResultExecutionFlow<FinalTypeReturn, ProblemType extends RuntimeException> {

    private Result<FinalTypeReturn, ProblemType> result;
    private FinalTypeReturn finalTypeReturn;
    private ProblemType problemType;

    public ResultExecutionFlow(FinalTypeReturn finalTypeReturn, ProblemType problemType) {
        this.finalTypeReturn = finalTypeReturn;
        this.problemType = problemType;
    }

    public static <FinalTypeReturn, ProblemType extends RuntimeException> ResultExecutionFlow<FinalTypeReturn, ProblemType> Success(FinalTypeReturn finalTypeReturn) {
        return new ResultExecutionFlow<>(finalTypeReturn, null);
    }

    public static <FinalTypeReturn, ProblemType extends RuntimeException> ResultExecutionFlow<FinalTypeReturn, ProblemType> fail(ProblemType problemType) {
        return new ResultExecutionFlow<>(null, problemType);
    }

    public ResultExecutionFlow<FinalTypeReturn, ProblemType> throwsEarlyIf(Class<? extends RuntimeException> exceptionType, Function<ProblemType, ? extends RuntimeException> exceptionMapper) throws RuntimeException {
        if (problemType != null && problemType.getClass().equals(exceptionType)) {
            throw exceptionMapper.apply(this.problemType);
        }
        return this;
    }

    public FinalTypeReturn execute() {
        return this.finalTypeReturn != null ? this.finalTypeReturn : null;
    }

}
