window.addEventListener("beforeunload", async () => {
  await fetch("/logout", { method: "POST" });
});