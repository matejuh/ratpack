/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.handling.internal;

import ratpack.func.Factory;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class FactoryHandler implements DescribingHandler {

  private final Factory<? extends Handler> factory;
  private volatile Handler last;

  public FactoryHandler(Factory<? extends Handler> factory) {
    this.factory = factory;
  }

  @Override
  public void handle(Context context) throws Exception {
    Handler handler = factory.create();
    last = handler;
    handler.handle(context);
  }

  @Override
  public void describeTo(StringBuilder stringBuilder) {
    Handler last = this.last;
    if (last == null) {
      last = this;
    }

    DescribingHandlers.describeTo(last, stringBuilder);
  }
}
