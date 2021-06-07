function payCheck() {
    if (document.getElementById('payWithGold').checked) {
        document.getElementById('Gold').style.visibility = 'visible';
        document.getElementById('Gold').style.display = 'flex';
        document.getElementById('Money').style.visibility = 'hidden';
        document.getElementById('Money').style.display = 'none';
    } else if (document.getElementById('payWithMoney').checked) {
        document.getElementById('Money').style.visibility = 'visible';
        document.getElementById('Money').style.display = 'flex';
        document.getElementById('Gold').style.visibility = 'hidden';
        document.getElementById('Gold').style.display = 'none';
    }
}