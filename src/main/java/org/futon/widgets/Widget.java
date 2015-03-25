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
     * @param container A container widget.
     */
    public Widget(Container container) {
        this.container = container;
    }

    // FIXME How the hell will I initialize this object?
    private Waiter waiter;

    /**
     * Container of the widget.
     */
    private Container container;   // FIXME To initialize.

    /**
     * Do the operation needed on the widget synchronizing on it.
     *
     * @throws ObjectNotFoundException If there is no widget ready for syncronization.
     */
    protected void doAction() {
        int index = 0;
        boolean done = false;
        Exception ex = null;
        while (!done && index < MAX_RETRIES) {
            try {
                doOnTestable(findTestable());
                done = true;
            } catch (Exception e) {
                index++;
                ex = e;
                waiter.sleep();
                if (index % RELOAD_RANGE == 0) {
                    container.reload();
                }   // if (section != null && index % 10 == 0)
            }
        }   // while (!done && index < MAX_RETRIES)
        if (!done)
            throw new ObjectNotFoundException(ex);
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
}
