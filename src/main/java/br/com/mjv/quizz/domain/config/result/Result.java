package br.com.mjv.quizz.domain.config.result;

import java.util.function.Function;

public class Result<ProblemType extends RuntimeException, SuccessType> {
    private boolean isSuccess;
    private ProblemType problemType;
    private SuccessType successType;

    public Result(boolean isSuccess, ProblemType problemType, SuccessType successType) {
        this.isSuccess = isSuccess;
        this.problemType = problemType;
        this.successType = successType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public SuccessType getSuccessType() {
        return successType;
    }

    public <FinalTypeReturn> ResultExecutionFlow<FinalTypeReturn, ProblemType> ifSuccess(Function<SuccessType, FinalTypeReturn> function) {
        if (isSuccess) {
            FinalTypeReturn result = function.apply(successType);
            return ResultExecutionFlow.success(result);
        } else {
            return ResultExecutionFlow.problem(problemType);
        }
    }

    public static <SuccessType> Result<RuntimeException, SuccessType> successWithReturn(SuccessType successType) {
        return new Result<>(true, null, successType);
    }

    public static <ProblemType extends RuntimeException, SuccessType> Result<ProblemType, SuccessType> failWithProblem(ProblemType problemType) {
        return new Result<>(false, problemType, null);
    }
}
