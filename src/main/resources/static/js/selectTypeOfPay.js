function payCheck() {
    if (document.getElementById('buyGold').checked) {
        document.getElementById('Gold').style.visibility = 'visible';
        document.getElementById('Money').style.visibility = 'hidden';
    } else if (document.getElementById('buyMoney').checked) {
        document.getElementById('Money').style.visibility = 'visible';
        document.getElementById('Gold').style.visibility = 'hidden';
    }
}
