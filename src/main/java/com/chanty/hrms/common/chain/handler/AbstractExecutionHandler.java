package com.chanty.hrms.common.chain.handler;

import com.chanty.hrms.common.chain.ExecutionContext;
import com.chanty.hrms.common.chain.ExecutionState;

/**
 * Defines a handler for processing an execution context.
 *
 * @param <T> the type of the execution context
 */
public interface AbstractExecutionHandler<T extends ExecutionContext> {

  /**
   * Executes the handler logic using the provided execution context.
   *
   * @param context the execution context to process
   * @return the state indicating the result of the execution
   */
  ExecutionState execute(T context);
}
