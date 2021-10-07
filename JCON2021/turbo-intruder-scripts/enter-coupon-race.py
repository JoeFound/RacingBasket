# Simplest flood test where all requests are queued in the same connection before being sent
# Notes:
# - RequestEngine, FilterRegex, ... are classes that TurboEngine provides;
#   don't be concerned if your editor/IDE doesn't recognise them
# - target.req is the target endpoint from where we launched TurboIntruder
# - target.baseInput is the part of the request that was highlighted when we lanunched TurboIntruder
# - target.endpoint is the endpoint relative to the request from where we launched TurboIntruder
# - the values passed to engine.queue and RequestEngine can be hardcoded and
#    don't need to be taken from target necessarily
#
#  Useful resources:
#  - https://portswigger.net/research/turbo-intruder-embracing-the-billion-request-attack
#  - https://github.com/PortSwigger/turbo-intruder/blob/master/decorators.md


def queueRequests(target, wordlists):
    engine = RequestEngine(endpoint=target.endpoint,
                           concurrentConnections=500,
                           requestsPerConnection=200,
                           pipeline=False
                           )

    # the 'gate' argument blocks the final byte of each request until openGate is invoked
    for i in range(300):
        engine.queue(target.req, target.baseInput, gate='race1')

    # wait until every 'race1' tagged request is ready
    # then send the final byte of each request
    # (this method is non-blocking, just like queue)
    engine.openGate('race1')

    engine.complete(timeout=60)

# We want to filter out the safe response and keep only the wrong ones
@FilterRegex(r".*40\.0.*")
def handleResponse(req, interesting):
    table.add(req)
