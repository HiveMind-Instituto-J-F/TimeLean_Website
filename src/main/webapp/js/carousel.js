document.addEventListener("DOMContentLoaded", () => {
    const wrap = document.querySelector("#slides-wrap");
    const slides = Array.from(wrap.children);

    let speed = 1;
    let position = 0;

    wrap.innerHTML += wrap.innerHTML;

    function animate() {
        position -= speed;
        const totalWidth = wrap.scrollWidth / 2;

        if (Math.abs(position) >= totalWidth) {
            position = 0;
        }

        wrap.style.transform = `translateX(${position}px)`;
        requestAnimationFrame(animate);
    }

    animate();
});