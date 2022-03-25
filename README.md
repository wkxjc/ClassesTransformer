# ClassesTransformer

It's used to modify .class file use ASM.

Now it can be used to debounce click event, includes 
setOnClickListener(this)
setOnClickListener(object: OnClickListener...)
setOnClickListener{// lambda}

# TODO
Support onClick=method() in xml
Support super class implement OnClickListener interface and subclass implement onClick method.
Support ListView&RecyclerView onClick
Support UnknownView onClick