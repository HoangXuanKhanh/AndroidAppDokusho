package haken.dokemi.andoku.base

import java.text.DecimalFormat


fun currentMoney(price: Double): String {
    val formatter = DecimalFormat("###,###,###")
    return String.format("%s %s", formatter.format(price), "")
}
