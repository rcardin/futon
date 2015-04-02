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

import org.futon.actions.Clickable;
import org.futon.actions.Reloadable;

/**
 * A table.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class Table extends Widget implements Reloadable, Clickable {

    @Override
    public void click() {
        doAction();
    }

    /**
     * Returns the number of rows of the table.
     *
     * @return The number of rows.
     */
    public abstract int getRowCount();

    /**
     * Returns the number of columns of the table.
     *
     * @return The number of columns.
     */
    public abstract int getColumnCount();

    /**
     * Returns the value of the cell at position {@code (row, col)}.
     *
     * @param row Requested row
     * @param col Requested column
     * @return The value of the cell at position {@code (row, col)}.
     */
    public abstract String getCellValue(int row, int col);
}
