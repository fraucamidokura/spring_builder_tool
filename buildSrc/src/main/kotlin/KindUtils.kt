fun clusterExists(): Boolean{
    var clusters = "kind get clusters".runCommand();

    print("command result $clusters")
    return clusters.lines().any { it.trim() == "builder" }
}
