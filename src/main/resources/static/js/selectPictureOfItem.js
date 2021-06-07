function selectPictureOfItem() {
    if (document.getElementById('weapons').checked) {
        document.getElementById('weapon').style.visibility = 'visible';
        document.getElementById('weapon').style.display = 'flex';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('pad').style.display = 'none';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.display = 'none';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('pauldron').style.display = 'none';
        document.getElementById('helmet').style.visibility = 'hidden';
        document.getElementById('helmet').style.display = 'none';
    } else if (document.getElementById('pads').checked) {
        document.getElementById('pad').style.visibility = 'visible';
        document.getElementById('pad').style.display = 'flex';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('weapon').style.display = 'none';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.display = 'none';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('pauldron').style.display = 'none';
        document.getElementById('helmet').style.visibility = 'hidden';
        document.getElementById('helmet').style.display = 'none';
    } else if (document.getElementById('helmets').checked) {
        document.getElementById('helmet').style.visibility = 'visible';
        document.getElementById('helmet').style.display = 'flex';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('pad').style.display = 'none';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('weapon').style.display = 'none';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.display = 'none';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('pauldron').style.display = 'none';
    } else if (document.getElementById('gauntlets').checked) {
        document.getElementById('gauntlet').style.visibility = 'visible';
        document.getElementById('gauntlet').style.display = 'flex';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('pad').style.display = 'none';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('weapon').style.display = 'none';
        document.getElementById('pauldron').style.visibility = 'hidden';
        document.getElementById('pauldron').style.display = 'none';
        document.getElementById('helmet').style.visibility = 'hidden';
        document.getElementById('helmet').style.display = 'none';
    } else if (document.getElementById('pauldrons').checked) {
        document.getElementById('pauldron').style.visibility = 'visible';
        document.getElementById('pauldron').style.display = 'flex';
        document.getElementById('pad').style.visibility = 'hidden';
        document.getElementById('pad').style.display = 'none';
        document.getElementById('weapon').style.visibility = 'hidden';
        document.getElementById('weapon').style.display = 'none';
        document.getElementById('gauntlet').style.visibility = 'hidden';
        document.getElementById('gauntlet').style.display = 'none';
        document.getElementById('helmet').style.visibility = 'hidden';
        document.getElementById('helmet').style.display = 'none';
    }
}