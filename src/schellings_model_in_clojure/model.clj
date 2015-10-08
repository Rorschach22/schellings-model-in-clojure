(ns schellings-model-in-clojure.model)

(def default-similarity 0.3)
(def default-balance 0.5)
(def default-empty 0.1)

; Atoms that reflect the state of the GUI sliders
; These aren't good names (they probably shouldn't say
; "atom", for example) so feel free to make them better.
(def similarity-atom (atom default-similarity))
(def balance-atom (atom default-balance))
(def empty-atom (atom default-empty))

(def population (atom []))
(def positions (atom []))

(defn handle-neighbor-change
  "Called when the state of a neighboring position changes.
   The first argument will be the position atom that is being
   notified of the change, and the 4th and 5th arguments are
   the old and new states of the neighbor respectively. You
   might be able to use those to just directly update the red/blue
   counts for this position without requiring that it go look
   at its neighbors."
  [me key neighbor old-state new-state]
  ; You'll obviously want to replace this with some code that actually
  ; does something useful :-)
  ; If your positions contain individuals as agents, this is
  ; probably where you want to call send to handle whatever
  ; needs to be done. Otherwise everything will end up happening in
  ; the main thread.
  (println (str "I am " me " and my neighbor " neighbor " (key " key ") changed from " old-state " to " new-state)))

;; You may be able to leave this alone, but feel free to change it
;; if you decide to structure your solution differently.
(defn make-position
  "Create a position atom that contains an individual agent, or nil
  if there's no individual there."
  []
  (if (< (rand) @empty-atom)
    (atom nil)
    (let [color (if (< (rand) @balance-atom) :red :blue)
          individual (agent color)
          position (atom individual)]
      ; I need to have all the individuals together in
      ; a collection so I can `send` them all a "message"
      ; when, e.g., we hit the "Start" button.
      (swap! population conj individual)
      ; I'm not sure if I need all the positions, actually,
      ; but I found that useful for debugging.
      (swap! positions conj position)
      position)))

(defn extract-color
  "Takes an individual agent and returns the color of the individual
   at that position. You'll need to implement this so that it returns
   desired color, or 'white' if there's no individual there (i.e., we
   get passed nil)."
  [individual]
  ; This returns a totally random color so it should be quite
  ; obvious if you haven't dealt with this. You can specify colors
  ; with things like strings ("blue") or keywords (:red).
  (seesaw.color/color (rand-int 256)
                      (rand-int 256)
                      (rand-int 256)
                      (rand-int 256))
  )
