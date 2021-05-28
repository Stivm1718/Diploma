function selectPictureOfHero() {
    if (document.getElementById('gender_man').checked) {
        document.getElementById('man').style.visibility = 'visible';
        document.getElementById('woman').style.visibility = 'hidden';
    } else if (document.getElementById('gender_woman').checked) {
        document.getElementById('woman').style.visibility = 'visible';
        document.getElementById('man').style.visibility = 'hidden';
    }
}