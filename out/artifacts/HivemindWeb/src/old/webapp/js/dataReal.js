document.querySelectorAll('.toggle-visibility').forEach(button => {
  button.addEventListener('click', (e) => {
    // evitar qualquer comportamento default por segurança
    e.preventDefault();

    const row = button.closest('tr');
    const sensitiveCells = row.querySelectorAll('.sensitive');
    const eyeOpen = button.querySelector('.open');    // agora acha porque usámos classe
    const eyeClosed = button.querySelector('.closed');

    // Se quiser alternar com base no estado da classe do botão:
    const isActive = button.classList.toggle('eye-active'); // true agora se passou a eye-active

    // Alterna texto entre asteriscos e valor real
    sensitiveCells.forEach(cell => {
      const real = cell.dataset.real ?? '';
      if (isActive) {
        // mostrar valor real
        cell.textContent = real;
      } else {
        // voltar para asteriscos (mesma quantidade de caracteres)
        cell.textContent = '*'.repeat(real.length || 6);
      }
    });

    // Opcional: controlar ícones diretamente se não usar CSS
    if (eyeOpen && eyeClosed) {
      if (isActive) {
        eyeOpen.style.display = 'none';
        eyeClosed.style.display = 'inline';
      } else {
        eyeOpen.style.display = 'inline';
        eyeClosed.style.display = 'none';
      }
    }
  });
});
