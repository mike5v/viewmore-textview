# ViewMore TextView

ViewMore TextView allows you to use a TextView by hiding the content of the text by a number of established lines and to display all the content expanding the TextView by a collapsing/expanding animation. It's possibile use an ellipses text or a foregorund color gradient or both to hide the content. 🚀

Here some examples:

![example](https://github.com/mike5v/viewmore-textview/blob/master/1.gif) ![example](https://github.com/mike5v/viewmore-textview/blob/master/2.gif)

# Setup

In your Gradle dependencies add:
```
implemantation "it.mike5v:viewmore-textview:$latest_release"
```

# Usage

* visibleLines -> number of visible lines
* isExpanded -> state of textview (default is false)
* animationDuration -> duration of the animation in ms
* foregroundColor -> color of the foreground gradient layer (default is Transparent)
* ellipsizeText -> text to use instead of default ellipses
* isUnderlined -> underline the ellipsize text

```
    <it.mike5v.viewmoretextview.ViewMoreTextView
        android:id="@+id/viewMore"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="32dp"
        android:layout_marginTop="32dp"
        android:layout_marginRight="32dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:ellipsizeText="View More"
        app:duration="500"
        app:visibleLines="3"
        app:isExpanded="false"/>
```
