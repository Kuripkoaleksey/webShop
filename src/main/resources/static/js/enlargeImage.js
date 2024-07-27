function enlargeImage(img) {
    var modal = document.createElement('div');
    modal.style.position = 'fixed';
    modal.style.top = '0';
    modal.style.left = '0';
    modal.style.width = '100%';
    modal.style.height = '100%';
    modal.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    modal.style.display = 'flex';
    modal.style.justifyContent = 'center';
    modal.style.alignItems = 'center';

    var largeImg = document.createElement('img');
    largeImg.src = img.src;
    largeImg.style.maxWidth = '80%';
    largeImg.style.maxHeight = '80%';

    modal.appendChild(largeImg);
    modal.onclick = function() {
        document.body.removeChild(modal);
    };

    document.body.appendChild(modal);
}
