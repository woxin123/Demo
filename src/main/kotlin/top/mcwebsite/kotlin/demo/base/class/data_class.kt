package top.mcwebsite.kotlin.demo.base.`class`

/**
 *
public final class Client {
@NotNull
private final String name;

private final int postalCode;

@NotNull
public final String getName() {
return this.name;
}

public final int getPostalCode() {
return this.postalCode;
}

public Client(@NotNull String name, int postalCode) {
this.name = name;
this.postalCode = postalCode;
}

@NotNull
public final String component1() {
return this.name;
}

public final int component2() {
return this.postalCode;
}

@NotNull
public final Client copy(@NotNull String name, int postalCode) {
Intrinsics.checkNotNullParameter(name, "name");
return new Client(name, postalCode);
}

@NotNull
public String toString() {
return "Client(name=" + this.name + ", postalCode=" + this.postalCode + ")";
}

public int hashCode() {
return ((this.name != null) ? this.name.hashCode() : 0) * 31 + Integer.hashCode(this.postalCode);
}

public boolean equals(@Nullable Object paramObject) {
if (this != paramObject) {
if (paramObject instanceof Client) {
Client client = (Client)paramObject;
if (Intrinsics.areEqual(this.name, client.name) && this.postalCode == client.postalCode)
return true;
}
} else {
return true;
}
return false;
}
}
 */
data class Client(val name: String, val postalCode: Int) {

//    override fun toString() = "Client(name=$name, postalCode=$postalCode)"
//
//    override fun equals(other: Any?): Boolean {
//        if (other == null || other !is Client) {
//            return false
//        }
//        return name == other.name && postalCode == other.postalCode
//    }
}

fun main() {
    val client1 = Client("Alice", 322562)
    val client2 = Client("Alice", 322562)
    println(client1)
    println(client1 == client2)
    val processed = hashSetOf(Client("Alice", 322562))
    println(processed.contains(Client("Alice", 322562)))

    val (name, postalCode) = client1
    println("client1 name = $name, postalCode = $postalCode")
    val copyClient = client1.copy(name = "Bob")
    println("copyClient = $copyClient")
}