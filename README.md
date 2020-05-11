# SwingSnackBar

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
- [License]()

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
