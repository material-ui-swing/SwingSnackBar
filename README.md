# SwingSnackBar
![Maven Central](https://img.shields.io/maven-central/v/io.github.material-ui-swing/SwingSnackBar?style=for-the-badge)
<div align="center">
 <img src="https://i.ibb.co/f82msmc/Swing-Snack-Bar-ICON.png" alt="Swing-Snack-Bar-ICON">
 <p align="center">Swing SnackBar component</p>
</div>

## Overview

- [Introduction](https://github.com/vincenzopalazzo/SwingSnackBar/wiki/Introduction)
- [How use the component](https://github.com/vincenzopalazzo/SwingSnackBar/wiki/How-use-the-component)
- [How create custom style for component](https://github.com/vincenzopalazzo/SwingSnackBar/wiki/How-create-custom-style-for-component)
- [How contribute](https://github.com/vincenzopalazzo/SwingSnackBar/wiki/How-contribute)
- [Author](https://github.com/vincenzopalazzo/SwingSnackBar/wiki/Author)
- [License](https://github.com/material-ui-swing/SwingSnackBar/blob/master/LICENSE.md)

## Examples

### Swing SnackBar

```java
SnackBar.make(frame, "Do you like Swing Snackbar?", "OPEN")
     .setDuration(SnackBar.LENGTH_SHORT)
     .setAction(new AbstractSnackBarAction() {
         @Override
         public void mousePressed(MouseEvent e) { 
              System.out.println("Hello SnackBar");
         }
       }
     ).run();
```
[Source code](https://github.com/vincenzopalazzo/SwingSnackBar/blob/master/src/test/java/io/swingsnackbar/DemoSnackBar.java)

P.S: At the moment, the API can change, I want try to implement the same API of Android.

### Android

```java
Snackbar.make(contextView, R.string.item_removed_message, Snackbar.LENGTH_LONG)
    .setAction(R.string.undo, new OnClickListener() {
      @Override
      public void onClick(View v) {
        // Respond to the click, such as by undoing the modification that caused
        // this message to be displayed
      })
    }).show();
```
[Source](https://material.io/develop/android/components/snackbar/)

## Package

**Maven**

```xml
<dependency>
  <groupId>io.github.material-ui-swing</groupId>
  <artifactId>SwingSnackBar</artifactId>
  <version>0.0.1-rc1</version>
</dependency>
```

 _Gradle_

```
implementation 'io.github.material-ui-swing:SwingSnackBar:0.0.1-rc1'
```

Look also [here](https://search.maven.org/search?q=io.github.material-ui-swing)

## License

<div align="center">
  <img src="https://opensource.org/files/osi_keyhole_300X300_90ppi_0.png" width="150" height="150"/>
</div>

Copyright (C) 2020 Vincenzo Palazzo vincenzopalazzodev@gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, 
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE 
OR OTHER DEALINGS IN THE SOFTWARE.