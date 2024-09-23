package com.chanty.hrms.common.chain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Defines the possible states of execution for handlers in the chain. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExecutionState {
  /** Indicates that the next handler in the chain should be executed. */
  NEXT,
  /** Indicates that the next handler in the chain should be skipped. */
  SKIP_NEXT,
  /** Indicates that the execution should end and no further handlers should be executed. */
  END
}
