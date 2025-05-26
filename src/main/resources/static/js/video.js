const player = videojs('video', {
    // Opcional: puedes ajustar configuraciones iniciales aquí
});

document.addEventListener('keydown', function (e) {
    if (document.activeElement.tagName.toLowerCase() === 'input') return;

    const skipSeconds = 10;

    switch (e.key) {
        case 'ArrowRight':
            player.currentTime(player.currentTime() + skipSeconds);
            break;
        case 'ArrowLeft':
            player.currentTime(player.currentTime() - skipSeconds);
            break;
        case 'd':
            player.currentTime(player.currentTime() + skipSeconds * 6);
            break;
        case 'a':
            player.currentTime(player.currentTime() - skipSeconds * 6);
            break;
        case ' ':
            // Previene el scroll al usar barra espaciadora
            e.preventDefault();
            if (player.paused()) {
                player.play();
            } else {
                player.pause();
            }
            break;
    }
});