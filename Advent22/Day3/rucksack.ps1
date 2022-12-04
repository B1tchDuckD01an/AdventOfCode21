
function Find-Matches {
    param(
        $contents1,
        $contents2
    )


}
$score = 0
$alphabet = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
Get-Content .\input.txt | ForEach-Object {
$a = $_.ToCharArray()

$pouchlen = $a.Length/2

$pouch1 = $a[0..($pouchlen -1)]
$pouch2 = $a[$pouchlen..$a.Length]
$found = $false
    $pouch2 | ForEach-Object {
    if(!$found)
    {
        #Write-Host "checking " $pouch1 "against" $pouch2
        if($pouch1.Contains($_))
        {
            Write-Host $alphabet.IndexOf($_)
            $score += $alphabet.IndexOf($_)
            $found = $true
        }
    }
}
}
Write-Host $score
