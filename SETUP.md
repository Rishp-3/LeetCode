# LeetCode Sync - Complete Setup Guide (Rishp-3/LeetCode)

## Is zip mein kya hai
- `.github/workflows/leetcode-sync.yml` : daily scheduled workflow (03:17 UTC) + manual run
- `scripts/leetcode_sync.py`            : sync script (sirf Python standard library)
- `.gitattributes`                      : Java source ke line endings ko change hone se rokta hai
- `SETUP.md`                            : ye guide

## Step 1: Files repo mein daalo
Zip extract karo. `.github/`, `scripts/`, `.gitattributes` ko `Rishp-3/LeetCode` repo ke root mein copy karo
(SETUP.md optional hai). Commit karke default branch par push karo.

## Step 2: LeetCode cookies nikalo
1. leetcode.com kholo aur login karo.
2. `F12` dabao -> DevTools khulega.
3. **Application** tab kholo (na dikhe to `>>` par click karo).
4. Left panel: Storage -> **Cookies** ke left wale **arrow** par click karke expand karo.
5. `https://leetcode.com` par click karo.
6. Table mein ye do rows dhundo (Filter box mein `LEETCODE` / `csrf` likh sakte ho):
   - `LEETCODE_SESSION`  -> lambi value (eyJ... se shuru)
   - `csrftoken`        -> chhoti value
7. Row select karke poori **Value** copy karo (aage-peeche space/quotes nahi aane chahiye).

Firefox mein: DevTools -> **Storage** tab -> Cookies -> https://leetcode.com.

## Step 3: GitHub secrets banao
Repo -> **Settings** -> **Secrets and variables** -> **Actions** -> **New repository secret**

| Name                  | Secret (value)                         |
| --------------------- | -------------------------------------- |
| `LEETCODE_SESSION`    | `LEETCODE_SESSION` cookie ki value     |
| `LEETCODE_CSRF_TOKEN` | `csrftoken` cookie ki value            |

Name ki spelling bilkul same rakho. Ye values kisi ke saath share mat karo, code/repo mein mat daalo.

## Step 4: Push permission do
Repo -> **Settings** -> **Actions** -> **General** -> **Workflow permissions**
-> **Read and write permissions** select karo -> **Save**.

## Step 5: Pehla full backfill
Repo -> **Actions** tab -> **LeetCode Sync** -> **Run workflow** -> `full_sync` tick karo -> **Run workflow**.
Khatam hone ke baad repo mein Easy/Medium/Hard folders, Solution.java, README.md aur root README ka progress table aa jayega.
Uske baad daily run apne aap incremental chalega.

## Kya hota hai
- `Solution.java` = latest accepted Java submission, byte-for-byte (existing Solution.java overwrite hogi).
- Har distinct accepted Java submission `<problem>/submissions/<timestamp>_<id>.java` mein archive hoti hai
  (band karne ke liye workflow mein `ARCHIVE_ALL_ACCEPTED: "false"`).
- Per-problem README.md sirf tab banti hai jab na ho.
- Existing `Main.java` ko kabhi modify nahi kiya jata; missing ho to chhota stub ban jata hai
  (band karne ke liye `CREATE_MAIN_STUB: "false"`). Workflow mein safety check bhi hai jo Main.java badalne par run fail kar deta hai.
- Root README.md: `<!-- LEETCODE-SYNC:START -->` aur `<!-- LEETCODE-SYNC:END -->` ke beech ka block auto-update hota hai;
  baaki likha hua safe rehta hai.

## Problems aaye to
- **Auth error / 403**: cookies expire ho gayi (kuch hafton mein hoti hain). Step 2 dobara karke secrets update karo.
  Agar cookies sahi hain phir bhi 403 aaye, LeetCode GitHub runner ki IP block kar raha ho sakta hai; local run karo:
  `LEETCODE_SESSION=... LEETCODE_CSRF_TOKEN=... FULL_SYNC=true python scripts/leetcode_sync.py`
- **Push fail**: Step 4 ki permission check karo.
- Sirf accepted **Java** submissions sync hoti hain, sirf leetcode.com (leetcode.cn nahi).
- Premium problems ka description nahi aata (placeholder lagta hai), par solution save hota hai.
