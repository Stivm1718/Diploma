function payCheck() {
    if (document.getElementById('payWithGold').checked) {
        document.getElementById('Gold').style.visibility = 'visible';
        document.getElementById('Money').style.visibility = 'hidden';
    } else if (document.getElementById('payWithMoney').checked) {
        document.getElementById('Money').style.visibility = 'visible';
        document.getElementById('Gold').style.visibility = 'hidden';
    }
}