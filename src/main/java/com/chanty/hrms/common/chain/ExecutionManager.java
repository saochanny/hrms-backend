package com.chanty.hrms.common.chain;

import com.chanty.hrms.common.chain.handler.AbstractExecutionHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * Manages and executes a chain of {@link AbstractExecutionHandler} instances.
 *
 * @param <T> the type of the execution context
 */
@Slf4j
public class ExecutionManager<T extends ExecutionContext> implements InitializingBean {

  private final List<AbstractExecutionHandler<T>> handlers = new ArrayList<>();

  /** Default constructor for the ExecutionManager. */
  protected ExecutionManager() {}

  /**
   * Constructor with class name logging.
   *
   * @param className the name of the class being initialized
   */
  protected ExecutionManager(String className) {
    if (log.isInfoEnabled()) {
      log.info("Class {} have been initializing", className);
    }
  }

  /**
   * Initializes the bean.
   *
   * <p>This method is part of the {@link InitializingBean} interface and is called after properties
   * are set.
   *
   * @throws Exception if initialization fails
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    // implement logic here
  }

  /**
   * Adds a handler to the execution chain.
   *
   * @param handler the handler to add
   */
  public void addHandler(AbstractExecutionHandler<T> handler) {
    handlers.add(handler);
  }

  /**
   * Adds multiple handlers to the execution chain.
   *
   * @param handlers the list of handlers to add
   */
  public void addHandlers(List<AbstractExecutionHandler<T>> handlers) {
    this.handlers.addAll(handlers);
  }

  /**
   * Executes the chain of handlers with the given execution context.
   *
   * @param context the execution context to process
   */
  public void execute(T context) {
    Iterator<AbstractExecutionHandler<T>> iterator = handlers.iterator();
    ExecutionState state;

    do {
      if (!iterator.hasNext()) {
        return;
      }
      AbstractExecutionHandler<T> handler = iterator.next();
      log.info("The {} is executing.", handler.getClass().getName());
      state = handler.execute(context);

      if (ExecutionState.SKIP_NEXT == state) {
        var clazz = iterator.next();
        log.info("Skip execution of {}", clazz.getClass().getName());
      }
    } while (ExecutionState.NEXT == state || ExecutionState.SKIP_NEXT == state);
  }
}
