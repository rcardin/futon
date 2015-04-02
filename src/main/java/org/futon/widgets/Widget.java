/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Riccardo Cardin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.futon.widgets;

import org.futon.Testable;
import org.futon.actions.Reloadable;
import org.futon.exceptions.ObjectNotFoundException;
import org.futon.utils.Waiter;

/**
 * Generic widget.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class Widget {

    /**
     * Default number of retries of synchronization on the widget.
     */
    private static final int MAX_RETRIES = 100;

    /**
     * Default range of container reloading.
     */
    private static final int RELOAD_RANGE = 10;

    /**
     * Ctor.
     */
    public Widget() {
        // Empty body
    }

    /**
     * Construct a widget inside a container.
     *
     * @param reloadable A container widget.
     */
    public Widget(Reloadable reloadable) {
        this.reloadable = reloadable;
    }

    // FIXME How the hell will I initialize this object?
    private Waiter waiter;

    /**
     * Container of the widget.
     */
    private Reloadable reloadable;   // FIXME To initialize.

    /**
     * <p>Do the operation needed on the widget synchronizing on it.</p>
     * <p>The method is a template method, completed by #waitForTestable
     *    and #doOnTestable.
     * </p>
     *
     * @throws ObjectNotFoundException If there is no widget ready for syncronization.
     */
    protected final Testable doAction() {
        return sync(new Function() {
            @Override
            public Testable apply() {
                Testable testable = waitForTestable();
                doOnTestable(testable);
                return testable;
            }
        });
    }

    /**
     * Retries to apply the {@code function} until the testable object is available and returns it.
     * The function is applied for a max of #MAX_RETRIES times. Every time, the process wait for
     * a little time, using the policy defined in the #waiter. Every #RELOAD_RANGE times, the
     * #container of the object is reloaded.
     *
     * @param function The function to be applied repeatedly.
     * @return The testable object resulting from the application of the {@code function}
     */
    protected final Testable sync(Function function) {
        int index = 0;
        boolean done = false;
        Exception ex = null;
        Testable testable = null;
        while (!done && index < MAX_RETRIES) {
            try {
                testable = function.apply();
                done = true;
            } catch (Exception e) {
                index++;
                ex = e;
                waiter.sleep();
                if (index % RELOAD_RANGE == 0) {
                    reloadable.reload();
                }   // if (section != null && index % 10 == 0)
            }
        }   // while (!done && index < MAX_RETRIES)
        if (!done)
            throw new ObjectNotFoundException(ex);
        return testable;
    }

    /**
     * <p>Find a testable object inside a #container. The operation in synchronized on
     *    the testable object.
     * </p>
     * <p>The method is a template method, completed by #findTestable.</p>
     *
     * @return A testable object.
     */
    protected final Testable waitForTestable() {
        return sync(new Function() {
            @Override
            public Testable apply() {
                return findTestable();
            }
        });
    }

    /**
     * Find the platform specific object associated to the widget.
     *
     * @return A platform specific object.
     */
    protected abstract Testable findTestable();

    /**
     * Do the operation associated to the widget on the platform specific object.
     *
     * @param testable Platform specific object.
     */
    protected abstract void doOnTestable(Testable testable);

    /**
     * A function to be done in a synchronous that returns a testable object.
     *
     * @see #sync(org.futon.widgets.Widget.Function)
     */
    private interface Function {
        /**
         * Execute the function to retrieve a testable object.
         *
         * @return A testable object.
         */
        public Testable apply();
    }
}
