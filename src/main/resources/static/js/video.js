const player = videojs('video', {
    // Opcional: puedes ajustar configuraciones iniciales aquí
});

document.addEventListener('keydown', function (e) {
    // Verificamos que no estemos escribiendo en un input para evitar conflictos
    if (document.activeElement.tagName.toLowerCase() === 'input') return;

    const skipSeconds = 10;
    const volumeStep = 0.05;

    if (e.ctrlKey) {
        switch (e.key) {
            case 'ArrowRight':
                e.preventDefault(); // Evita bookmarks en algunos navegadores
                player.currentTime(player.currentTime() + skipSeconds * 6);
                return;
            case 'ArrowLeft':
                e.preventDefault(); // Evita bookmarks en algunos navegadores
                player.currentTime(player.currentTime() - skipSeconds * 6);
                return;
        }
    }

    switch (e.key) {
        case 'ArrowRight':
            player.currentTime(player.currentTime() + skipSeconds);
            break;
        case 'ArrowLeft':
            player.currentTime(player.currentTime() - skipSeconds);
            break;
        case ' ':
            // Evitamos el scroll al usar la barra espaciadora
            e.preventDefault();
            if (player.paused()) {
                player.play();
            } else {
                player.pause();
            }
            break;
        case 'a':
            player.currentTime(player.currentTime() - skipSeconds * 6);
            break;
        case 'd':
            player.currentTime(player.currentTime() + skipSeconds * 6);
            break;
        case 'w':
        case 'ArrowUp':
            e.preventDefault();
            player.volume(Math.min(player.volume() + volumeStep, 1));
            break;
        case 's':
        case 'ArrowDown':
            e.preventDefault();
            player.volume(Math.max(player.volume() - volumeStep, 0));
            break;
        case 'q':
        case 'm':
            player.muted(!player.muted());
            break;
    }
});
