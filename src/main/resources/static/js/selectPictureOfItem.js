function selectPictureOfItem() {
    if (document.getElementById('weapons').checked) {
        document.getElementById('weapon').style.visibility = 'visible';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('helmet').style.visibility = 'hidden';
    } else if (document.getElementById('pads').checked) {
        document.getElementById('pad').style.visibility = 'visible';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('helmet').style.visibility = 'hidden';
    } else if (document.getElementById('helmets').checked) {
        document.getElementById('helmet').style.visibility = 'visible';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('pauldron').style.visibility = 'hidden';
    } else if (document.getElementById('gauntlets').checked) {
        document.getElementById('gauntlet').style.visibility = 'visible';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('helmet').style.visibility = 'hidden';
    } else if (document.getElementById('pauldrons').checked) {
        document.getElementById('pauldron').style.visibility = 'visible';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('helmet').style.visibility = 'hidden';
    }
}