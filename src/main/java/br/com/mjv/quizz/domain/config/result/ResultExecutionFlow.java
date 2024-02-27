package br.com.mjv.quizz.domain.config.result;

import java.util.Optional;
import java.util.function.Function;

public class ResultExecutionFlow<FinalTypeReturn, ProblemType extends RuntimeException> {

    private final FinalTypeReturn finalTypeReturn;
    private final ProblemType problemType;

    public ResultExecutionFlow(FinalTypeReturn finalTypeReturn, ProblemType problemType) {
        this.finalTypeReturn = finalTypeReturn;
        this.problemType = problemType;
    }

    public FinalTypeReturn getFinalTypeReturn() {
        return finalTypeReturn;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public static <FinalTypeReturn, ProblemType extends RuntimeException> ResultExecutionFlow<FinalTypeReturn, ProblemType> success(FinalTypeReturn finalTypeReturn) {
        return new ResultExecutionFlow<>(finalTypeReturn, null);
    }

    public static <FinalTypeReturn, ProblemType extends RuntimeException> ResultExecutionFlow<FinalTypeReturn, ProblemType> problem(ProblemType problemType) {
        return new ResultExecutionFlow<>(null, problemType);
    }

    public <ExceptionType extends RuntimeException> ResultExecutionFlow<FinalTypeReturn, ProblemType> throwsEarlyIf(Class<ExceptionType> classeProblema, Function<ExceptionType, ? extends RuntimeException> funcao) {
        if(problemType != null && problemType.getClass().equals(classeProblema)) {
            throw funcao.apply((ExceptionType)this.problemType);
        }
        return this;
    }

    public Optional<FinalTypeReturn> execute() {
        if (problemType != null) {
            return Optional.empty();
        }
        return Optional.ofNullable(finalTypeReturn);
    }
}

