# BlueMarketFinal

Se presenta un proyecto final de la asignatura Programación 2, el cual usa web services, archivos SQL, stack
tecnológicos y frameworks para su ejecución. En el cual se cumple con los requerimientos:

1. Crear un usuario artista o comprador. Para poder autenticarse en la tienda, todos los usuarios deben tener un correo
   electrónico y una contraseña. Adicionalmente, deben tener un nombre y una cantidad inicial de FCoins igual a 0.0.
2. Autenticar usuario. Utilizando el correo electrónico y la contraseña, el usuario podrá autenticarse en la tienda.
   Dependiendo de su rol deberá ser dirigido a una interfaz diferente.
4. Crear piezas de arte. Una vez creada la colección, al artista se le debe permitir crear piezas de arte digital las
   cuales tendrán los atributos de título, precio dado en FCoins y una imágen. Tenga en cuenta que el creador de la
   pieza, el artista, debe ser su primer propietario.
5. Recargar cuenta. Si es un usuario comprador, se le debe permitir recargar su wallet con FCoins.
6. Ver piezas. Si es un usuario comprador, podrá ver la lista de piezas disponibles con todos sus atributos, incluyendo
   el nombre, imágen, propietario, precio, colección y si está disponible para la venta.
7. Comprar pieza. A partir de la lista de piezas, un comprador podrá comprar una pieza de arte que esté disponible para
   la venta. Para realizar la compra, el comprador debe tener la cantidad requerida de FCoins. Una vez que se realiza la
   compra, se debe registrar el ingreso y salida de FCoins de las wallets del vendedor y del comprador, según
   corresponda.
9. Cambiar precio. El propietario de una pieza, sea artista o comprador, podrá cambiar en cualquier momento el valor de
   la pieza de arte.
10. Dar like. Cualquier usuario comprador podrá dar like a la pieza de arte de su interés. Tenga en cuenta que un
    usuario puede dar solo un like a una pieza.

Modelo de datos
usado: ![image](https://user-images.githubusercontent.com/99420707/172074900-82030311-c676-400e-97cc-151ddcab9078.png)
