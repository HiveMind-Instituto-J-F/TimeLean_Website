let lastScroll = 0;
const header = document.querySelector("header");

window.addEventListener("scroll", () => {
  const currentScroll = window.pageYOffset;

  if (currentScroll > lastScroll) {
    // rolando para baixo → esconder
    header.classList.add("hide");
  } else {
    // rolando para cima → mostrar
    header.classList.remove("hide");
  }

  lastScroll = currentScroll;
});
