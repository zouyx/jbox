workflow "Test" {
  on = "push"
  resolves = ["gradle"]
}

action "gradle" {
  uses = "./gradle"
  args = "idea"
}
