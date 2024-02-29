package br.com.mjv.quizz.domain.config.result;

import java.util.function.Function;

public class Result<Success, Problem extends RuntimeException> {

    private Success success;
    private Problem problem;
    private boolean isError;

    public Result(Success success) {
        this.success = success;
        this.isError = false;
    }

    public Result(Problem problem) {
        this.problem = problem;
        this.isError = true;
    }

    public static <Success, Problem extends RuntimeException> Result<Success, Problem> successWithReturn(Success success) {
        return new Result<>(success);
    }

    public static <Success, Problem extends RuntimeException> Result<Success, Problem> failWithProblem(Problem problem) {
        return new Result<>(problem);
    }

    public <T> ResultExecutionFlow<T, Problem> ifSuccess(Function<Success, T> function) {
        if (isSuccess()) {
            return ResultExecutionFlow.Success(function.apply(success));
        } else {
            return ResultExecutionFlow.fail(problem);
        }
    }

    public Success getSuccess() {
        if (!isSuccess()) {
            throw new IllegalStateException("Result is not a success");
        }
        return this.success;
    }

    public Problem getProblem() {
        if (!isFailure()) {
            throw new IllegalStateException("Result is not a failure");
        }
        return this.problem;
    }

    public boolean isSuccess() {
        return !isError;
    }

    public boolean isFailure() {
        return isError;
    }
}
