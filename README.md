Compound
========

Java Library for storing data - complex or simple - in an easy and reliable format.

Important Classes
-----------------

###[`net.alexmack.compound.Compound`](https://github.com/alexmack929/compound/blob/master/src/net/alexmack/compound/Compound.java)<br />
Made up of case-insensetive *identifiers* and *elements*. Every *identifier* corresponds to an *element*, functioning exactly like a [Map](http://en.wikipedia.org/wiki/Associative_array). While an *identifier* must be a `String`, an *element* can be of various types, detailed at the bottom of this page. Reading is highly recommended.<br /><br />
**Important Methods**<br />
`set(String identifier, Object element)`<br />
Sets the *element* corresponding to the given *identifier* and returns the `Compound` instance, to allow easy statement chaining.<br />
`get(String identifier)`<br />
Returns the *element* corresponding to the given *identifier*. Variations of this method include `getCast`, which utilizes generic type casting and `getDefault` which returns a specified default value - rather than `null` - if there is no such *element*. These methods and others are further detailed in the source code.

###[`net.alexmack.compound.io.CompoundIO`](https://github.com/alexmack929/compound/blob/master/src/net/alexmack/compound/io/CompoundIO.java)<br />
The `CompoundIO` class provides I/O methods for reading and writing `Compound` instances. I/O operations in the Compound Library are carefully designed to:
- Write elements in a linear fashion, handling recursion properly. This means that instances which contain themselves as elements will not cause any problems, unlike other formats.
- Write every instance only once, no matter how many times it is specified as an element in one structure.
- Properly retain structure, no matter how complex.

`CompoundIO` will, however, only store elements of the allowed types - detailed at the bottom of this page.<br /><br >
**Important Methods**<br />
`write(Compound compound[, ??? output])`<br />
Writes the given instance using the output provided. If no output is provided, a formatted `String` will be returned. Accepted output classes are listed below.<br />
`read(??? input)`<br />
Returns a `Compound` read from the given input. Accepted input classes are listed below. Additionally, the `readNull` method accepts all the same input but will return `null` should any errors occur, negating the need for a TRYCATCH statement.<br />

###Tables

`CompoundIO` methods:

|I/O Method      |Accepted Input |Accpeted Output |
|----------------|---------------|----------------|
|Formatted String|String         |*N/A*           |
|File            |File           |File            |
|Stream          |DataInputStream|DataOutputStream|
|Operator        |CompoundInput  |CompoundOutput  |

`CompoundIO` operators:

|Operator        |Input Class         |Output Class        |
|----------------|--------------------|--------------------|
|Stream          |CompoundInputStream |CompoundOutputStream|
|Bytes           |CompoundInputBytes  |CompoundOutputBytes |
|String          |CompoundInputString |CompoundOutputString|
