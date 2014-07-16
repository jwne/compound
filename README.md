Compound
========

Java Library for storing data - complex or simple - in an easy and reliable format.<br />
Code examples can be found at the bottom of this README, I recommend reading the whole thing.

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

###Information

`CompoundIO` Methods:

|I/O Method      |Accepted Input |Accpeted Output |
|----------------|---------------|----------------|
|Formatted String|String         |*N/A*           |
|File            |File           |File            |
|Stream          |DataInputStream|DataOutputStream|
|Operator        |CompoundInput  |CompoundOutput  |

`CompoundIO` Operators:

|Operator        |Input Class         |Output Class        |
|----------------|--------------------|--------------------|
|Stream          |CompoundInputStream |CompoundOutputStream|
|Bytes           |CompoundInputBytes  |CompoundOutputBytes |
|String          |CompoundInputString |CompoundOutputString|

Accepted Elements:

|Name            |ID  |Hex  |Accepted Classes             |Accepted Primitives        |
|----------------|----|-----|-----------------------------|---------------------------|
|UNKNOWN         |????|-0x80|*N/A*                        |*N/A*                      |
|COMPOUND        |comp|-0x7F|`Compound`                   |None                       |
|NULL            |null|0x00 |None                         |`null`                     |
|BOOLEAN         |bool|0x01 |`Boolean`                    |`boolean`                  |
|INTEGER         |intg|0x02 |`Byte`, `Short`, `Integer`   |`byte`, `short`, `int`     |
|INTEGER_LONG    |intl|0x03 |`Long`                       |`long`                     |
|DOUBLE          |dobl|0x04 |`Float`, `Double`            |`float`, `double`          |
|STRING          |strg|0x05 |`String`                     |None                       |
|BIG_INTEGER     |bigi|0x06 |`BigInteger`                 |None                       |
|BIG_DECIMAL     |bigd|0x07 |`BigDecimal`                 |None                       |
*Elements which are not of a type listed in the table above will not be written by I/O methods.*

###Example Code

####Basic Operations:<br />
Code:
```java
	public static void main(String[] args) {
		// Create a new Compound.
		Compound compound = new Compound();
		// Set an element.
		compound.set("helloworld", "Hello World!");
		// Output the element.
		System.out.println(compound.get("helloworld"));
	}
```
Output:
```
Hello World!
```
####Recursive Compounds:<br />
Code:
```java
	public static void main(String[] args) {
		// Create a new Compound.
		Compound outer = new Compound();
		// Create another Compound.
		Compound inner = new Compound();
		// Set the second Compound as an element in the first.
		outer.set("inner", inner);
		// Set an element in the second Compound.
		inner.set("message", "Hello World!");
		// Get the second Compound from the first and output the message within.
		System.out.println(((Compound) outer.get("inner")).get("message"));
	}
```
Output:
```
Hello World!
```

####IO: Writing a Compound String:<br/>
Code:
```java
	public static void main(String[] args) {
		// Create a new Compound.
		Compound compound = new Compound();
		// Set some elements.
		compound.set("luckynumber", 7);
		compound.set("helloworld", "Hello World!");

		try {
			// Write a formatted String with IO and output it.
			System.out.println(CompoundIO.write(compound));
		}catch (Exception e) {
			System.out.println("Something went wrong!");
		}
	}
```
Output:
```
2/helloworld/5/Hello+World%21/luckynumber/2/7/1
```

####IO: Reading a Compound String:<br />
Code:
```java
	public static void main(String[] args) {
		// The formatted String.
		String input = "2/helloworld/5/Hello+World%21/luckynumber/2/7/1";
		
		try {
			// Read the Compound from the String with IO.
			Compound compound = CompoundIO.read(input);
			// Output some elements.
			System.out.println(compound.get("helloworld"));
			System.out.println(compound.get("luckynumber"));
		}catch (Exception e) {
			System.out.println("Something went wrong!");
		}
	}
```
Output:
```
Hello World!
7
```

####Complex Compounds #1:<br />
Code:
```java
	public static void main(String[] args) {
		// Create a new Compound to represent a person.
		Compound person = new Compound();
		// Set various elements.
		person.set("name", "Abraham Lincoln");
		person.set("born", "February 12, 1809");
		person.set("died", "April 15, 1865");
		person.set("age", 56);
		person.set("nationality", "American");
		
		// Create another Compound to represent a birthplace.
		Compound birthplace = new Compound();
		birthplace.set("nation", "United States of America");
		birthplace.set("state", "Kentucky");
		birthplace.set("city", "Hodgenville");
		
		// Create another Compound to represent children.
		// The CompoundConversion class is used to convert an array to a Compound.
		Compound children = CompoundConversion.toCompound(new String[] {
				"Robert Todd Lincoln",
				"Edward Baker Lincoln",
				"Willie Lincoln",
				"Tad Lincoln"
		});
		
		// Set the two Compounds as elements.
		person.set("birthplace", birthplace);
		person.set("children", children);
		
		try {
			// Write a formatted String, containg all three Compounds, with IO and output it.
			System.out.println(CompoundIO.write(person));
		}catch (Exception e) {
			System.out.println("Something went wrong!");
		}
	}
```
Output:
```
7/born/5/February+12%2C+1809/nationality/5/American/birthplace/-3v/-8000000000000/died/5/April+15%2C+1865/age/2/1o/name/5/Abraham+Lincoln/children/-3v/-7vvvvvvvvvvvv/0/-8000000000000/3/state/5/Kentucky/nation/5/United+States+of+America/city/5/Hodgenville/0/-7vvvvvvvvvvvv/5/3/5/Tad+Lincoln/2/5/Willie+Lincoln/1/5/Edward+Baker+Lincoln/0/5/Robert+Todd+Lincoln/s/2/4/1
```

####Complex Compounds #2:<br />
Code:
```java
	public static void main(String[] args) {
		// The formatted String.
		String input = "7/born/5/February+12%2C+1809/nationality/5/American/birthplace/-3v/-8000000000000/died/5/April+15%2C+1865/age/2/1o/name/5/Abraham+Lincoln/children/-3v/-7vvvvvvvvvvvv/0/-8000000000000/3/state/5/Kentucky/nation/5/United+States+of+America/city/5/Hodgenville/0/-7vvvvvvvvvvvv/5/3/5/Tad+Lincoln/2/5/Willie+Lincoln/1/5/Edward+Baker+Lincoln/0/5/Robert+Todd+Lincoln/s/2/4/1";
		
		try {
			// Read the Compound from the String with IO.
			Compound compound = CompoundIO.read(input);
			// Output name.
			System.out.println("Name: " + compound.get("name"));
			// Output nationality.
			System.out.println("Nation: " + compound.get("nationality"));
			
			// Use CompoundConversion to create an array from the children element.
			Object[] children = CompoundConversion.toArray((Compound) compound.get("children"));
			
			// Output children.
			for (Object child : children) {
				System.out.println("Child: " + child);
			}
			
		}catch (Exception e) {
			System.out.println("Something went wrong!");
		}
	}
```
Output:
```
Name: Abraham Lincoln
Nation: American
Child: Robert Todd Lincoln
Child: Edward Baker Lincoln
Child: Willie Lincoln
Child: Tad Lincoln
```
