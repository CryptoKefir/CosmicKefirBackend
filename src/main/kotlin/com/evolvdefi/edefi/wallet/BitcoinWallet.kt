// class BitcoinWallet(
//   private val wallet: Wallet,
//   private val blockchain: Blockchain
// ) {
//   fun sync() { wallet.sync(Progress(), blockchain) }
//   fun getNewAddress(): String = wallet.getNewAddress(KeychainKind.EXTERNAL).address
//   fun getBalance(): ULong = wallet.getBalance().total().toULong()
//   fun send(to: String, sats: ULong, feeRate: Double = 1.0): String { /* ... */ }
// }