# futon
`futon` is a lightwheight meta-framework for automatic functional testing. It provides the primitives to interact with a
classic *web application*. These primitives are called **widgets**. The widgets supported by now are:
* Anchor
* Input text
* Button
* Simple check
* Checkboxes group
* Message's area
* Radiobutton group
* Select (HTML primitives)
* SelectJs (Drop down menus made using CSS stylesheets)
* Table
* Input text area
* Section
  * Tabbed section
  * Closable section

`futon` is totally based dynamic widget searching inside the container you define. `futon` suites perfectly modern browser webapp automatic functional testing, but you can implement your own version for a specific app type.

`futon` is a **meta-framework**, which means that you have to implement some methods, intentionally left abstract, using your functional testing specific platform features. Actually, there are planned to extensions of `futon`, respectively for [Selenium](http://www.seleniumhq.org/) and for [Rational Functional Tester](http://www-03.ibm.com/software/products/it/functional).

Stay tuned for any news about the development of `futon`!
