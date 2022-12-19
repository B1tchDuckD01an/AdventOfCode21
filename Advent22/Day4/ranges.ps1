$a = Get-Content .\input.txt
$score = 0

foreach($thing in $a)
{
    $pairs = $thing.Split(",")
    foreach($pair in $pairs[0])
    {
        $numbers = $pair.Split("-")
        $range1 = ($numbers[0]..$numbers[1])
        Write-Host 
        Write-Host ----- NEXT -----
        Write-Host $thing
        Write-Host $range1
    }
    foreach($pair in $pairs[1])
    {
        $numbers = $pair.Split("-")
        $range2 = ($numbers[0]..$numbers[1])
                Write-Host -----
        Write-Host $range2
    }

    if($range1[0] -ge $range2[0])
    {
        $comparison = $range1.Where{$_ -in $range2 }
            if($comparison.Count -eq $range1.Count)
    {
        Write-Host these completely appear in the range $comparison
        $score ++
    }
    }
    else
    {
        $comparison = $range2.Where{$_ -in $range1 }
    
    if($comparison.Count -eq $range2.Count)
    {
        Write-Host these completely appear in the range $comparison
        $score++
    }
    }
}
Write-Host $score